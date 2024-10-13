CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    user_status VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_on TIMESTAMP
);



CREATE TABLE services (
    service_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    duration_minutes INT,
    price INT,
    created_at TIMESTAMP NOT NULL,
    modified_on TIMESTAMP
);


CREATE TABLE appointments (
    appointment_id SERIAL PRIMARY KEY,
    appointment_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL,
    modified_on TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    service_id INT NOT NULL,
    client_id INT NOT NULL,
    employee_id INT NOT NULL,
    FOREIGN KEY (service_id) REFERENCES services(service_id),
    FOREIGN KEY (client_id) REFERENCES users(user_id),
    FOREIGN KEY (employee_id) REFERENCES users(user_id)
);



CREATE TABLE reviews (
    review_id SERIAL PRIMARY KEY,
    review_content TEXT,
    rating INT,
    created_at TIMESTAMP NOT NULL,
    modified_on TIMESTAMP,
    appointment_id INT NOT NULL,
    FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);
