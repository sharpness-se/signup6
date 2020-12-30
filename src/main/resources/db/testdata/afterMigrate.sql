-- a small test database for SignUp with some users, groups and events

INSERT INTO public.users VALUES (-1, 'Fredrik', 'Unknown', 'En glad statsminister', 'fredrik.unknown@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-2, 'Torbjörn', 'Fälldin', '', 'torbjörn.fälldin@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-3, 'Göran', 'Persson', 'En f.d. statsminister', 'göran.persson@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-4, 'Frodo', 'Baggins', 'Ringbärare', 'frodo.baggins@crisp.se', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
INSERT INTO public.users VALUES (-5, 'John', 'Doe', 'Okändis', 'john@doe.net', '', 'NormalUser', '*', 'Gravatar', NULL, NULL, NULL) ON CONFLICT DO NOTHING;
-- don't use use_id = -1

