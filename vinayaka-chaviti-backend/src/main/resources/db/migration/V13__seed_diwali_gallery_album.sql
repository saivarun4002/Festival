-- US-GALLERY: Seed a published "Diwali Celebrations" album with sample
-- Diwali-themed images so the public Gallery page has content out of the box.
INSERT INTO gallery_albums (title, cover_image_url, published, created_at, updated_at)
VALUES (
    'Diwali Celebrations',
    'https://loremflickr.com/800/600/diwali,diya',
    TRUE,
    NOW(6),
    NOW(6)
);

SET @diwali_album_id = LAST_INSERT_ID();

INSERT INTO gallery_images (album_id, image_url, caption, display_order, created_at) VALUES
(@diwali_album_id, 'https://loremflickr.com/800/600/diwali,diya', 'Rows of lit clay diyas', 0, NOW(6)),
(@diwali_album_id, 'https://loremflickr.com/800/600/rangoli,colorful', 'Colorful rangoli at the entrance', 1, NOW(6)),
(@diwali_album_id, 'https://loremflickr.com/800/600/fireworks,night', 'Fireworks lighting up the night sky', 2, NOW(6)),
(@diwali_album_id, 'https://loremflickr.com/800/600/indiansweets,festival', 'Traditional festival sweets', 3, NOW(6)),
(@diwali_album_id, 'https://loremflickr.com/800/600/diwali,lights', 'Festive string lights decoration', 4, NOW(6)),
(@diwali_album_id, 'https://loremflickr.com/800/600/temple,lamps', 'Temple illuminated with oil lamps', 5, NOW(6));
