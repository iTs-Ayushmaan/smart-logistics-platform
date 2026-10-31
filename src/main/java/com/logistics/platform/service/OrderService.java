package com.logistics.platform.service;

import com.logistics.platform.dto.CreateOrderDTO;
import com.logistics.platform.dto.OrderResponseDTO;
import com.logistics.platform.dto.UpdateOrderDTO;
import com.logistics.platform.dto.UpdateOrderStatusDTO;
import com.logistics.platform.entity.Driver;
import com.logistics.platform.entity.Order;
import com.logistics.platform.entity.Route;
import com.logistics.platform.entity.User;
import com.logistics.platform.repository.DriverRepository;
import com.logistics.platform.repository.OrderRepository;
import com.logistics.platform.repository.RouteRepository;
import com.logistics.platform.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {

    private static final Set<String> VALID_STATUSES = Set.of(
            "PENDING",
            "ASSIGNED",
            "IN_TRANSIT",
            "DELIVERED",
            "FAILED",
            "CANCELLED"
    );

    private static final Set<String> VALID_PRIORITIES = Set.of(
            "LOW",
            "NORMAL",
            "HIGH",
            "URGENT"
    );

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DriverRepository driverRepository;
    private final RouteRepository routeRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            DriverRepository driverRepository,
            RouteRepository routeRepository
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.driverRepository = driverRepository;
        this.routeRepository = routeRepository;
    }

    // ---------------- ADMIN QUERIES ----------------

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(normalizeStatus(status))
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByCustomerId(Integer customerId) {
        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByDriverId(Integer driverId) {
        return orderRepository.findByAssignedDriverId(driverId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByRouteId(Integer routeId) {
        return orderRepository.findByAssignedRouteId(routeId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<OrderResponseDTO> getOrdersByPriority(String priority) {
        return orderRepository.findByPriority(normalizePriority(priority))
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ---------------- ROLE-AWARE QUERIES ----------------

    public OrderResponseDTO getOrderById(
            Integer id,
            Authentication authentication
    ) {
        Order order = findOrderOrThrow(id);
        assertCanView(order, authentication);

        return toResponseDTO(order);
    }

    // Logged-in customer's orders.
    public List<OrderResponseDTO> getMyOrders(
            Authentication authentication
    ) {
        User customer = currentUser(authentication);

        return orderRepository.findByCustomerId(customer.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // Logged-in driver's assigned deliveries.
    public List<OrderResponseDTO> getMyDeliveries(
            Authentication authentication
    ) {
        Driver driver = currentDriver(authentication);

        return orderRepository.findByAssignedDriverId(driver.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ---------------- CREATE ORDER: CUSTOMER ----------------

    public OrderResponseDTO createOrder(
            CreateOrderDTO request,
            Authentication authentication
    ) {
        User customer = currentUser(authentication);

        validateTimeWindow(
                request.getTimeWindowStart(),
                request.getTimeWindowEnd()
        );

        Order order = new Order();

        // Never accept customer ID from the request body.
        order.setCustomerId(customer.getId());

        order.setPickupLatitude(request.getPickupLatitude());
        order.setPickupLongitude(request.getPickupLongitude());

        order.setDeliveryLatitude(request.getDeliveryLatitude());
        order.setDeliveryLongitude(request.getDeliveryLongitude());

        order.setDeliveryAddress(request.getDeliveryAddress().trim());

        order.setPackageWeightKg(request.getPackageWeightKg());
        order.setPackageUnits(request.getPackageUnits());

        order.setTimeWindowStart(request.getTimeWindowStart());
        order.setTimeWindowEnd(request.getTimeWindowEnd());

        order.setPriority(normalizePriority(request.getPriority()));
        order.setDeliveryNotes(request.getDeliveryNotes());

        // Customer cannot assign a driver, route or status.
        order.setStatus("PENDING");
        order.setAssignedDriverId(null);
        order.setAssignedRouteId(null);

        return toResponseDTO(orderRepository.save(order));
    }

    // ---------------- UPDATE ORDER: ADMIN ----------------

    public OrderResponseDTO updateOrder(
            Integer id,
            UpdateOrderDTO request
    ) {
        Order order = findOrderOrThrow(id);

        if (request.getDeliveryAddress() != null) {
            if (request.getDeliveryAddress().isBlank()) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Delivery address cannot be blank"
                );
            }

            order.setDeliveryAddress(request.getDeliveryAddress().trim());
        }

        if (request.getPackageWeightKg() != null) {
            order.setPackageWeightKg(request.getPackageWeightKg());
        }

        if (request.getPackageUnits() != null) {
            order.setPackageUnits(request.getPackageUnits());
        }

        if (request.getTimeWindowStart() != null) {
            order.setTimeWindowStart(request.getTimeWindowStart());
        }

        if (request.getTimeWindowEnd() != null) {
            order.setTimeWindowEnd(request.getTimeWindowEnd());
        }

        validateTimeWindow(
                order.getTimeWindowStart(),
                order.getTimeWindowEnd()
        );

        if (request.getPriority() != null) {
            order.setPriority(normalizePriority(request.getPriority()));
        }

        if (request.getPickupLatitude() != null) {
            order.setPickupLatitude(request.getPickupLatitude());
        }

        if (request.getPickupLongitude() != null) {
            order.setPickupLongitude(request.getPickupLongitude());
        }

        if (request.getDeliveryLatitude() != null) {
            order.setDeliveryLatitude(request.getDeliveryLatitude());
        }

        if (request.getDeliveryLongitude() != null) {
            order.setDeliveryLongitude(request.getDeliveryLongitude());
        }

        if (request.getDeliveryNotes() != null) {
            order.setDeliveryNotes(request.getDeliveryNotes());
        }

        // Validate driver assignment.
        if (request.getAssignedDriverId() != null) {
            Integer driverId = request.getAssignedDriverId();

            Driver driver = driverRepository.findById(driverId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Driver not found: " + driverId
                    ));

            if (order.getAssignedRouteId() != null
                    && request.getAssignedRouteId() == null) {
                Route existingRoute = routeRepository
                        .findById(order.getAssignedRouteId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Existing assigned route was not found"
                        ));

                if (!existingRoute.getDriverId().equals(driver.getId())) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "The assigned route belongs to another driver. Update the route assignment too."
                    );
                }
            }

            boolean newlyAssigned =
                    !driverId.equals(order.getAssignedDriverId());

            order.setAssignedDriverId(driverId);

            // First driver assignment moves a pending order to ASSIGNED.
            if (newlyAssigned && "PENDING".equals(order.getStatus())) {
                order.setStatus("ASSIGNED");
            }
        }

        // Validate route assignment.
        if (request.getAssignedRouteId() != null) {
            Integer routeId = request.getAssignedRouteId();

            Route route = routeRepository.findById(routeId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Route not found: " + routeId
                    ));

            if (order.getAssignedDriverId() != null
                    && !order.getAssignedDriverId().equals(route.getDriverId())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "The selected route belongs to a different driver"
                );
            }

            order.setAssignedRouteId(routeId);

            // If no driver was assigned yet, use the route's driver.
            if (order.getAssignedDriverId() == null) {
                order.setAssignedDriverId(route.getDriverId());

                if ("PENDING".equals(order.getStatus())) {
                    order.setStatus("ASSIGNED");
                }
            }
        }

        // Explicit admin status update takes precedence.
        if (request.getStatus() != null) {
            order.setStatus(normalizeStatus(request.getStatus()));
        }

        updateStatusTimestamps(order);

        return toResponseDTO(orderRepository.save(order));
    }

    // ---------------- DRIVER STATUS UPDATE ----------------

    public OrderResponseDTO updateStatusAsDriver(
            Integer orderId,
            UpdateOrderStatusDTO request,
            Authentication authentication
    ) {
        Driver driver = currentDriver(authentication);
        Order order = findOrderOrThrow(orderId);

        if (!driver.getId().equals(order.getAssignedDriverId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "This order is not assigned to you"
            );
        }

        String newStatus = normalizeStatus(request.getStatus());
        String currentStatus = order.getStatus();

        boolean allowed =
                ("ASSIGNED".equals(currentStatus)
                        && "IN_TRANSIT".equals(newStatus))
                        ||
                        ("IN_TRANSIT".equals(currentStatus)
                                && ("DELIVERED".equals(newStatus)
                                || "FAILED".equals(newStatus)));

        if (!allowed) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status transition: "
                            + currentStatus + " -> " + newStatus
            );
        }

        order.setStatus(newStatus);

        if ("IN_TRANSIT".equals(newStatus)) {
            order.setPickupTime(LocalDateTime.now());
        }

        if ("DELIVERED".equals(newStatus)) {
            order.setActualDeliveryTime(LocalDateTime.now());
        }

        if (request.getDeliveryNotes() != null) {
            order.setDeliveryNotes(request.getDeliveryNotes());
        }

        return toResponseDTO(orderRepository.save(order));
    }

    // ---------------- DELETE ORDER: ADMIN ----------------

    public void deleteOrder(Integer id) {
        Order order = findOrderOrThrow(id);
        orderRepository.delete(order);
    }

    // ---------------- HELPER METHODS ----------------

    private Order findOrderOrThrow(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Order not found"
                ));
    }

    private User currentUser(Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authentication required"
            );
        }

        User user = userRepository.findByUsername(authentication.getName());

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "User no longer exists"
            );
        }

        return user;
    }

    private Driver currentDriver(Authentication authentication) {
        User user = currentUser(authentication);

        List<Driver> drivers = driverRepository.findByUserId(user.getId());

        if (drivers.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No driver profile is linked to this account"
            );
        }

        if (drivers.size() > 1) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Multiple driver profiles are linked to this account"
            );
        }

        return drivers.get(0);
    }

    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_" + role)
                );
    }

    private void assertCanView(
            Order order,
            Authentication authentication
    ) {
        if (hasRole(authentication, "ADMIN")) {
            return;
        }

        if (hasRole(authentication, "CUSTOMER")) {
            User customer = currentUser(authentication);

            if (customer.getId().equals(order.getCustomerId())) {
                return;
            }
        }

        if (hasRole(authentication, "DRIVER")) {
            Driver driver = currentDriver(authentication);

            if (driver.getId().equals(order.getAssignedDriverId())) {
                return;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not allowed to view this order"
        );
    }

    private void validateTimeWindow(
            LocalDateTime start,
            LocalDateTime end
    ) {
        if (start == null || end == null || !end.isAfter(start)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Time window end must be after time window start"
            );
        }
    }

    private String normalizePriority(String priority) {
        if (priority == null || priority.isBlank()) {
            return "NORMAL";
        }

        String value = priority.trim().toUpperCase();

        if (!VALID_PRIORITIES.contains(value)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid priority. Allowed values: " + VALID_PRIORITIES
            );
        }

        return value;
    }

    private String normalizeStatus(String status) {
        if (status == null || status.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status is required"
            );
        }

        String value = status.trim().toUpperCase();

        if (!VALID_STATUSES.contains(value)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid status. Allowed values: " + VALID_STATUSES
            );
        }

        return value;
    }

    private void updateStatusTimestamps(Order order) {
        if ("IN_TRANSIT".equals(order.getStatus())
                && order.getPickupTime() == null) {
            order.setPickupTime(LocalDateTime.now());
        }

        if ("DELIVERED".equals(order.getStatus())
                && order.getActualDeliveryTime() == null) {
            order.setActualDeliveryTime(LocalDateTime.now());
        }
    }

    private OrderResponseDTO toResponseDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .pickupLatitude(order.getPickupLatitude())
                .pickupLongitude(order.getPickupLongitude())
                .deliveryLatitude(order.getDeliveryLatitude())
                .deliveryLongitude(order.getDeliveryLongitude())
                .deliveryAddress(order.getDeliveryAddress())
                .packageWeightKg(order.getPackageWeightKg())
                .packageUnits(order.getPackageUnits())
                .timeWindowStart(order.getTimeWindowStart())
                .timeWindowEnd(order.getTimeWindowEnd())
                .priority(order.getPriority())
                .status(order.getStatus())
                .assignedDriverId(order.getAssignedDriverId())
                .assignedRouteId(order.getAssignedRouteId())
                .pickupTime(order.getPickupTime())
                .actualDeliveryTime(order.getActualDeliveryTime())
                .deliveryNotes(order.getDeliveryNotes())
                .build();
    }
}