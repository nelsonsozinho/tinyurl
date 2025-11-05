create table public.tiny_url(
    id BIGSERIAL not null primary key,
    original_url character varying(255) not null,
    tiny_url character varying(255)
)