**Trading tool for Bitmex**

*Spring Boot 2, OAuth2 / Spring Security, Jpa - Hibernate, Spring Data, MySQL, Swagger API Docs*

Sign up as a follower and `Follow` a trader. Every order he makes in Bitmex, it's placed for you, too.\
Sign up as trader and start the trading.


- The app is a multi-tenant system with 4 basic roles (root, admin, trader, follower).

- The admins, traders and followers are getting grouped by a tenant and all their data and actions are isolated on their tenant.

- The unique root user(who is tenant-independent) is considered the owner of the application, and has access on all data and actions/can control everything.

- The admin/admins are considered the owner/owners of their tenant, and they are managing only their traders and followers that are registered on their tenant.  

- Anyone can freely register as a trader or as a follower, specifying any known user (follower, trader or admin of any tenant) he wants to get grouped with.
Note that it's required to already know the username of the user to search with.

- The root user can create as many tenants and as many admins (specifying the tenant) he wants.

- During the first startup, it initializes with the unique root user, with 1 tenant and with 1 admin, 1 trader and 1 follower users, that are grouped on this tenant.

>username/password: root/root, admin1/pass, trader1/pass, follower1/pass




