-- =========================
-- Base tables (no inheritance table for BaseModel since it's @MappedSuperclass)
-- =========================

-- Driver
CREATE TABLE driver (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    name VARCHAR(255),
    license_number VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255)
);

-- Passenger
CREATE TABLE passenger (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    name VARCHAR(255)
);

-- Booking
CREATE TABLE booking (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    booking_status VARCHAR(50),
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    total_distance BIGINT,

    driver_id BIGINT,
    passenger_id BIGINT,

    CONSTRAINT fk_booking_driver
        FOREIGN KEY (driver_id)
        REFERENCES driver(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_booking_passenger
        FOREIGN KEY (passenger_id)
        REFERENCES passenger(id)
        ON DELETE SET NULL
);

-- =========================
-- Review (Parent Table - JOINED inheritance)
-- =========================

CREATE TABLE booking_review (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,

    content VARCHAR(255) NOT NULL,
    rating DOUBLE,

    booking_id BIGINT NOT NULL UNIQUE,

    CONSTRAINT fk_review_booking
        FOREIGN KEY (booking_id)
        REFERENCES booking(id)
        ON DELETE CASCADE
);

-- =========================
-- PassengerReview (Child Table)
-- JOINED inheritance → PK is also FK
-- =========================

CREATE TABLE passenger_review (
    id BIGINT PRIMARY KEY,

    passenger_review_content VARCHAR(255) NOT NULL,
    passenger_rating VARCHAR(255) NOT NULL,

    CONSTRAINT fk_passenger_review_parent
        FOREIGN KEY (id)
        REFERENCES booking_review(id)
        ON DELETE CASCADE
);