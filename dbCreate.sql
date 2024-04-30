DROP TABLE IF EXISTS rol_permiso;
DROP TABLE IF EXISTS rol_usuario; 
DROP TABLE IF EXISTS permiso;
DROP TABLE IF EXISTS rol; 
DROP TABLE IF EXISTS tarea; 
DROP TABLE IF EXISTS usuario; 


CREATE TABLE "rol" (
	"id" BIGSERIAL PRIMARY KEY,
	"nombre" varchar
);

CREATE TABLE "permiso"(
	"id" BIGSERIAL PRIMARY KEY,
	"nombre" varchar
);

CREATE TABLE "usuario" (
                           "id" BIGSERIAL PRIMARY KEY,
                           "username" varchar UNIQUE NOT NULL,
                           "password" varchar,
                           "isEnabled" boolean,
                           "accountNoexpired" boolean,
                           "accountNoLocked" boolean,
                           "credentialNoExpired" boolean
);


CREATE TABLE "tarea" (
	"id" BIGSERIAL PRIMARY KEY,
	"titulo" varchar,
	"descripcion" varchar,
	"expira" Date,
	"completado" boolean,
    "id_usuario" BIGINT REFERENCES "usuario" ("id")
);

CREATE TABLE "rol_permiso"(
	"id" BIGSERIAL PRIMARY KEY,
	"id_permiso" BIGINT REFERENCES "permiso" ("id"),
	"id_rol" BIGINT REFERENCES "rol" ("id")
);

CREATE TABLE "rol_usuario" (
	"id" BIGSERIAL PRIMARY KEY,
	"id_usuario" BIGINT REFERENCES "usuario" ("id"),
	"id_rol" BIGINT REFERENCES "rol" ("id")
);

CREATE INDEX idx_username ON "usuario" ("username");
