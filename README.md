# SNI Reverse Proxy

### About

Simple HTTPS reverse proxy that denies requests from users without the SNI extension. When a user with the correct certificate makes a request, this request is passed along to a given HTTP backend server.

### Requirements

This project was developed using the following technologies:

- **Java** version 11.0.4
- **Apache Maven** version 3.6.0

### Environment Variables

| Variable                          |  Description                         | Example                         |
|-----------------------------------|--------------------------------------|---------------------------------|
| SNI_REVERSE_PROXY_PORT            | Port in which reverse proxy will run | 8080                            |
| SNI_REVERSE_PROXY_BACKEND_ADDRESS | Address of backend to which redirect | localhost                       |
| SNI_REVERSE_PROXY_BACKEND_PORT    | Port of backend to which redirect    | 8081                            |

### Running

Once you have installed Java and Maven, and exported the above environment variables, you can run this project by simply typing `make run`.
