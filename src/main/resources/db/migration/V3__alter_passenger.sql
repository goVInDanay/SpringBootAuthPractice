
ALTER TABLE passenger
ADD COLUMN phone_number VARCHAR(255),
ADD COLUMN email VARCHAR(255),
ADD COLUMN password VARCHAR(255);

UPDATE passenger
SET email = 'default@example.com',
    password = 'default_password'
WHERE email IS NULL;

ALTER TABLE passenger
MODIFY name VARCHAR(255) NOT NULL,
MODIFY email VARCHAR(255) NOT NULL,
MODIFY password VARCHAR(255) NOT NULL;