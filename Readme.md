# PostgreSQL Setup with Docker and Table Schema

## Steps to Set Up

### 1. Run PostgreSQL with Docker

To start a PostgreSQL container with Docker, use the following command:

```bash
docker run --name postgres -e POSTGRES_DB=postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```

- `POSTGRES_DB=postgres`: The name of the database.
- `POSTGRES_USER=postgres`: The username for the database.
- `POSTGRES_PASSWORD=postgres`: The password for the database.
- `-p 5432:5432`: Maps the local port 5432 to the PostgreSQL container’s port.
- `-d`: Runs the container in detached mode.

### 2. Enable `pgcrypto` Extension

Once the container is running, you need to enable the `pgcrypto` extension to use `gen_random_uuid()` for generating
random UUIDs.

Run the following SQL command:

```sql
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
```

### 3. Create the Database Tables

After enabling the extension, create the `requests` and `contacts` tables by running the following SQL commands:

```sql
-- Create the 'requests' table
CREATE TABLE requests (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    brand VARCHAR NOT NULL,
    request_type VARCHAR NOT NULL,
    sent_date DATE NOT NULL,
    contact_name VARCHAR NOT NULL,
    contact_number VARCHAR NOT NULL
);

-- Create the 'contacts' table
CREATE TABLE contacts (
    id VARCHAR PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR NOT NULL,
    number VARCHAR NOT NULL,
    request_id VARCHAR NOT NULL,
    CONSTRAINT fk_request FOREIGN KEY (request_id) REFERENCES requests(id) ON DELETE CASCADE
);
```

- **requests**: Stores information about requests with fields like `brand`, `request_type`, `sent_date`, `contact_name`,
  and `contact_number`.
- **contacts**: Stores contact information linked to each request, using `request_id` as a foreign key to `requests`.
