# College Election / Online Voting System
Java Swing + MySQL + JDBC mini-project, following the supplied report's desktop architecture.

## Setup
1. Install JDK (8+; JDK 17 recommended) and MySQL Community Server.
2. Run `database/javaDatabase.sql` in MySQL Workbench.
3. Download MySQL Connector/J and place the `.jar` in `lib/`.
4. Edit `config.properties`; set your MySQL username/password.
5. Windows: run `run.bat`. Linux/macOS: `chmod +x run.sh && ./run.sh`.

## Sample logins
Students: `01 / 0101`, `02 / 0202`, `03 / 0303`, `04 / 0404`
Admins: `91 / 9191`, `92 / 9292`

## Demo sequence
Admin starts election -> students register before election starts -> admin starts election -> each student votes once -> admin stops election -> view results. Reset requires admin password.

## Note
This is a classroom prototype. Sample passwords are plain text as in the report's prototype description. Do not use for public or high-stakes elections. Use only synthetic demo data.
