-- ============================================
-- V1 - CREATE CORE TABLES
-- Logistics & Route Optimization Platform
-- ============================================


-- ============================================
-- 1. USERS
-- ============================================

CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(255) UNIQUE NOT NULL,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email
    ON users(email);

CREATE INDEX idx_users_role
    ON users(role);


-- ============================================
-- 2. DRIVERS
-- ============================================

CREATE TABLE drivers (
                         id SERIAL PRIMARY KEY,

                         user_id INTEGER NOT NULL
                             REFERENCES users(id) ON DELETE CASCADE,

                         name VARCHAR(255) NOT NULL,

                         phone VARCHAR(20) NOT NULL,

                         vehicle_type VARCHAR(50),

                         vehicle_capacity_kg DECIMAL(10, 2) NOT NULL,

                         vehicle_capacity_units INTEGER NOT NULL,

                         current_latitude DECIMAL(10, 8),

                         current_longitude DECIMAL(10, 8),

                         status VARCHAR(50) DEFAULT 'INACTIVE',

                         availability_start_time TIME,

                         availability_end_time TIME,

                         total_deliveries INTEGER DEFAULT 0,

                         rating DECIMAL(3, 2),

                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_drivers_status
    ON drivers(status);

CREATE INDEX idx_drivers_user_id
    ON drivers(user_id);

ALTER TABLE drivers
    ADD CONSTRAINT check_capacity_positive
        CHECK (
            vehicle_capacity_kg > 0
                AND vehicle_capacity_units > 0
            );


-- ============================================
-- 3. ROUTES
-- ============================================

CREATE TABLE routes (
                        id SERIAL PRIMARY KEY,

                        driver_id INTEGER NOT NULL
                            REFERENCES drivers(id) ON DELETE CASCADE,

                        route_date DATE NOT NULL,

                        status VARCHAR(50) DEFAULT 'PLANNED',

                        estimated_total_distance_km DECIMAL(10, 2),

                        actual_total_distance_km DECIMAL(10, 2),

                        estimated_total_time_minutes INTEGER,

                        actual_total_time_minutes INTEGER,

                        estimated_cost_rupees DECIMAL(10, 2),

                        actual_cost_rupees DECIMAL(10, 2),

                        total_orders INTEGER,

                        completed_orders INTEGER DEFAULT 0,

                        route_sequence JSONB,

                        optimized_by VARCHAR(50) DEFAULT 'GREEDY',

                        optimization_timestamp TIMESTAMP,

                        optimization_duration_seconds DECIMAL(10, 3),

                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_routes_driver_id
    ON routes(driver_id);

CREATE INDEX idx_routes_date
    ON routes(route_date);

CREATE INDEX idx_routes_status
    ON routes(status);


-- ============================================
-- 4. ORDERS
-- ============================================

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,

                        customer_id INTEGER NOT NULL
                            REFERENCES users(id) ON DELETE CASCADE,

                        pickup_latitude DECIMAL(10, 8) NOT NULL,

                        pickup_longitude DECIMAL(10, 8) NOT NULL,

                        delivery_latitude DECIMAL(10, 8) NOT NULL,

                        delivery_longitude DECIMAL(10, 8) NOT NULL,

                        delivery_address TEXT NOT NULL,

                        package_weight_kg DECIMAL(10, 2) NOT NULL,

                        package_units INTEGER NOT NULL,

                        time_window_start TIMESTAMP NOT NULL,

                        time_window_end TIMESTAMP NOT NULL,

                        priority VARCHAR(50) DEFAULT 'NORMAL',

                        status VARCHAR(50) DEFAULT 'PENDING',

                        assigned_driver_id INTEGER
                            REFERENCES drivers(id) ON DELETE SET NULL,

                        assigned_route_id INTEGER
                            REFERENCES routes(id) ON DELETE SET NULL,

                        pickup_time TIMESTAMP,

                        actual_delivery_time TIMESTAMP,

                        delivery_notes TEXT,

                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                        updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_orders_status
    ON orders(status);

CREATE INDEX idx_orders_customer_id
    ON orders(customer_id);

CREATE INDEX idx_orders_driver_id
    ON orders(assigned_driver_id);

CREATE INDEX idx_orders_time_window
    ON orders(time_window_start, time_window_end);

CREATE INDEX idx_orders_priority
    ON orders(priority);

-- PostGIS geographic index from the document
-- is intentionally omitted because PostGIS
-- is not installed on your PostgreSQL server.


-- ============================================
-- 5. ROUTE ORDERS
-- ============================================

CREATE TABLE route_orders (
                              id SERIAL PRIMARY KEY,

                              route_id INTEGER NOT NULL
                                  REFERENCES routes(id) ON DELETE CASCADE,

                              order_id INTEGER NOT NULL
                                  REFERENCES orders(id) ON DELETE CASCADE,

                              sequence_number INTEGER NOT NULL,

                              estimated_arrival_time TIMESTAMP,

                              actual_arrival_time TIMESTAMP,

                              estimated_departure_time TIMESTAMP,

                              actual_departure_time TIMESTAMP,

                              status VARCHAR(50) DEFAULT 'PENDING',

                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_route_orders_route_id
    ON route_orders(route_id);

CREATE INDEX idx_route_orders_order_id
    ON route_orders(order_id);

CREATE UNIQUE INDEX idx_route_orders_unique
    ON route_orders(route_id, order_id);


-- ============================================
-- 6. GPS LOGS
-- ============================================

CREATE TABLE gps_logs (
                          id BIGSERIAL PRIMARY KEY,

                          driver_id INTEGER NOT NULL
                              REFERENCES drivers(id) ON DELETE CASCADE,

                          route_id INTEGER
                                            REFERENCES routes(id) ON DELETE SET NULL,

                          current_order_id INTEGER
                                            REFERENCES orders(id) ON DELETE SET NULL,

                          latitude DECIMAL(10, 8) NOT NULL,

                          longitude DECIMAL(10, 8) NOT NULL,

                          speed_kmh DECIMAL(6, 2),

                          accuracy_meters DECIMAL(8, 2),

                          timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_gps_logs_driver_timestamp
    ON gps_logs(driver_id, timestamp DESC);

CREATE INDEX idx_gps_logs_timestamp
    ON gps_logs(timestamp DESC);

-- PostGIS geographic index omitted because
-- PostGIS is not installed.


-- ============================================
-- 7. AUDIT LOGS
-- ============================================

CREATE TABLE audit_logs (
                            id SERIAL PRIMARY KEY,

                            actor_id INTEGER
                                                    REFERENCES users(id) ON DELETE SET NULL,

                            entity_type VARCHAR(50) NOT NULL,

                            entity_id INTEGER NOT NULL,

                            action VARCHAR(50) NOT NULL,

                            old_value JSONB,

                            new_value JSONB,

                            reason TEXT,

                            timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_audit_logs_entity
    ON audit_logs(entity_type, entity_id);

CREATE INDEX idx_audit_logs_timestamp
    ON audit_logs(timestamp DESC);


-- ============================================
-- 8. ROUTE DECISIONS
-- ============================================

CREATE TABLE route_decisions (
                                 id SERIAL PRIMARY KEY,

                                 route_id INTEGER NOT NULL
                                     REFERENCES routes(id) ON DELETE CASCADE,

                                 decision_status VARCHAR(50) NOT NULL,

                                 risk_score DECIMAL(3, 2),

                                 total_constraint_violations INTEGER,

                                 time_window_violations INTEGER,

                                 capacity_violations INTEGER,

                                 availability_violations INTEGER,

                                 decision_reason TEXT,

                                 suggested_action VARCHAR(500),

                                 approved_by INTEGER
                                     REFERENCES users(id) ON DELETE SET NULL,

                                 approved_at TIMESTAMP,

                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_route_decisions_route_id
    ON route_decisions(route_id);

CREATE INDEX idx_route_decisions_status
    ON route_decisions(decision_status);