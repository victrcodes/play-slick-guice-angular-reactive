CREATE TABLE "order" (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	date TIMESTAMP NOT NULL DEFAULT now(),
	name VARCHAR NOT NULL,
	description VARCHAR
);

INSERT INTO "order" (name, description) VALUES ('Nexus', 'Google Nexus 6P 128 GB Graphite');
INSERT INTO "order" (name, description) VALUES ('Pixel', 'Google Pixel LS 2015');