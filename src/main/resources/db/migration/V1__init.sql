
-- ---------------------------------------------------------
-- 1. block — a zone within the yard
-- ---------------------------------------------------------
CREATE TABLE block (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    code              VARCHAR(20) NOT NULL UNIQUE,
    allowed_category  ENUM('DRY', 'REEFER', 'HAZMAT') NULL
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- 2. slot — one physical parking space for a container
--    'version' is the optimistic-locking column.
-- ---------------------------------------------------------
CREATE TABLE slot (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    code              VARCHAR(20) NOT NULL UNIQUE,
    size_capacity     VARCHAR(5)  NOT NULL,          -- '20' or '40'
    supports_reefer   BOOLEAN     NOT NULL DEFAULT FALSE,
    hazmat_approved   BOOLEAN     NOT NULL DEFAULT FALSE,
    status            ENUM('FREE', 'OCCUPIED', 'BLOCKED') NOT NULL DEFAULT 'FREE',
    block_id          BIGINT      NOT NULL,
    version           BIGINT      NOT NULL DEFAULT 0,
    CONSTRAINT fk_slot_block
        FOREIGN KEY (block_id) REFERENCES block(id)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- 3. vessel_call — one ship's visit to the terminal
-- ---------------------------------------------------------
CREATE TABLE vessel_call (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    vessel_name       VARCHAR(100) NOT NULL,
    voyage_number     VARCHAR(50)  NOT NULL,
    eta               DATETIME     NULL,
    status            ENUM('PLANNED', 'DISCHARGING', 'LOADING', 'DEPARTED') NOT NULL DEFAULT 'PLANNED'
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- 4. container — the core object being managed
-- ---------------------------------------------------------
CREATE TABLE container (
    id                 BIGINT AUTO_INCREMENT PRIMARY KEY,
    container_number   VARCHAR(20) NOT NULL UNIQUE,   -- ISO 6346, e.g. MSKU1234565
    iso_type           VARCHAR(10) NOT NULL,           -- e.g. 42G1
    category           ENUM('DRY', 'REEFER', 'HAZMAT') NOT NULL,
    weight_kg          DECIMAL(10,2) NOT NULL,
    status             ENUM('EXPECTED', 'YARDED', 'ON_HOLD', 'DEPARTED') NOT NULL DEFAULT 'EXPECTED',
    vessel_call_id     BIGINT NULL,
    current_slot_id    BIGINT NULL,
    yarded_at          DATETIME NULL,
    CONSTRAINT fk_container_vessel_call
        FOREIGN KEY (vessel_call_id) REFERENCES vessel_call(id),
    CONSTRAINT fk_container_slot
        FOREIGN KEY (current_slot_id) REFERENCES slot(id)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- 5. container_history — append-only audit trail
-- ---------------------------------------------------------
CREATE TABLE container_history (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    container_id      BIGINT NOT NULL,
    from_status       ENUM('EXPECTED', 'YARDED', 'ON_HOLD', 'DEPARTED') NULL,
    to_status         ENUM('EXPECTED', 'YARDED', 'ON_HOLD', 'DEPARTED') NOT NULL,
    reason            VARCHAR(255) NULL,
    timestamp         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_history_container
        FOREIGN KEY (container_id) REFERENCES container(id)
) ENGINE=InnoDB;

-- ---------------------------------------------------------
-- 6. hold — a block placed on a container
-- ---------------------------------------------------------
CREATE TABLE hold (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    container_id      BIGINT NOT NULL,
    hold_type         ENUM('CUSTOMS', 'DAMAGE', 'UNPAID_FEES') NOT NULL,
    reason            VARCHAR(255) NULL,
    active            BOOLEAN NOT NULL DEFAULT TRUE,
    placed_at         DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    released_at       DATETIME NULL,
    CONSTRAINT fk_hold_container
        FOREIGN KEY (container_id) REFERENCES container(id)
) ENGINE=InnoDB;

-- =========================================================
-- Sanity check queries (run after creation)
-- =========================================================
-- SHOW TABLES;
-- DESCRIBE container;
-- SELECT * FROM information_schema.table_constraints WHERE table_schema = DATABASE();