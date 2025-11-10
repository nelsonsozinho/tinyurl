alter table public.tiny_url add column date_register date not null default now();
alter table public.tiny_url add column date_expiring date not null default now();

UPDATE public.tiny_url
SET date_expiring = tiny_url.date_expiring + INTERVAL '1 month';