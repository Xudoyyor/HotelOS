-- Har bir servis uchun alohida ma'lumotlar bazasini yaratish
CREATE DATABASE reception_db;
CREATE DATABASE housekeeping_db;
CREATE DATABASE auth_db;
CREATE DATABASE room_db;
CREATE DATABASE maintenance_db;

-- Izoh: Standart 'postgres' foydalanuvchisi (superuser) orqali hamma servis ulanadi, 
-- lekin ularning ma'lumotlari alohida bazalarda toza saqlanadi.
