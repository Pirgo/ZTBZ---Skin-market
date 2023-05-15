db.createUser(
    {
        user: "ztbzBackend",
        pwd: "example",
        roles: [
            {
                role: "readWrite",
                db: "ztbzDatabase"
            }
        ]
    }
);
