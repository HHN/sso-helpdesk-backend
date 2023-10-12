--- We cannot decrypt old passwords after using a new secret, so mark them as used.
update account_credential set used = CURRENT_TIMESTAMP where used IS NULL;