SELECT id, name, access_level, user_id
FROM roles
JOIN user_roles ON (id = role_id)
WHERE user_id = ?