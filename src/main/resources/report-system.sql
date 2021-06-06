ALTER TABLE IF EXISTS ONLY public.plant DROP CONSTRAINT IF EXISTS pk_plant_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.plant DROP CONSTRAINT IF EXISTS fk_inventory_item_location_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.plant DROP CONSTRAINT IF EXISTS fk_listing_status_id CASCADE;
ALTER TABLE IF EXISTS ONLY public.plant DROP CONSTRAINT IF EXISTS fk_marketplace_id CASCADE;

ALTER TABLE IF EXISTS ONLY public.location DROP CONSTRAINT IF EXISTS pk_location_id CASCADE;

ALTER TABLE IF EXISTS ONLY public.listing_status DROP CONSTRAINT IF EXISTS pk_listing_status_id CASCADE;

ALTER TABLE IF EXISTS ONLY public.marketplace DROP CONSTRAINT IF EXISTS pk_marketplace_id CASCADE;

DROP TABLE IF EXISTS public.plant;
CREATE TABLE plant (
                        id UUID NOT NULL,
                        title TEXT NOT NULL,
                        description TEXT NOT NULL,
                        inventory_item_location_id UUID NOT NULL,
                        listing_price DECIMAL NOT NULL,
                        currency character(3),
                        quantity INTEGER NOT NULL,
                        listing_status INTEGER NOT NULL,
                        marketplace INTEGER NOT NULL,
                        upload_time TIMESTAMP WITHOUT TIME ZONE,
                        owner_email_address TEXT NOT NULL
);

DROP TABLE IF EXISTS public.location;
CREATE TABLE location (
                      id UUID NOT NULL,
                      manager_name TEXT,
                      phone TEXT NOT NULL,
                      address_primary TEXT,
                      address_secondary TEXT,
                      country TEXT,
                      town TEXT,
                      postal_code TEXT
);

DROP TABLE IF EXISTS public.listing_status;
CREATE TABLE listing_status (
                      id INTEGER NOT NULL,
                      status_name TEXT NOT NULL
);

DROP TABLE IF EXISTS public.marketplace;
CREATE TABLE marketplace (
                       id INTEGER NOT NULL,
                       marketplace_name TEXT NOT NULL
);

ALTER TABLE ONLY plant
    ADD CONSTRAINT pk_plant_id PRIMARY KEY (id);

ALTER TABLE ONLY location
    ADD CONSTRAINT pk_location_id PRIMARY KEY (id);

ALTER TABLE ONLY listing_status
    ADD CONSTRAINT pk_listing_status_id PRIMARY KEY (id);

ALTER TABLE ONLY marketplace
    ADD CONSTRAINT pk_marketplace_id PRIMARY KEY (id);

ALTER TABLE ONLY plant
    ADD CONSTRAINT fk_inventory_item_location_id FOREIGN KEY (inventory_item_location_id) REFERENCES location(id);

ALTER TABLE ONLY plant
    ADD CONSTRAINT fk_marketplace_id FOREIGN KEY (marketplace) REFERENCES marketplace(id);

ALTER TABLE ONLY plant
    ADD CONSTRAINT fk_listing_status_id FOREIGN KEY (listing_status) REFERENCES listing_status(id);