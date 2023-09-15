create type gender as ENUM ('MALE','FEMALE');
ALTER TABLE student ALTER column gender TYPE gender
using (gender::gender);