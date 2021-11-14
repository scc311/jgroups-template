# Shared

This is a good directory to store content that both clients and the frontend server will need to access to (such as encryption keys).

To use this folder, when running your containers from the root directory of this repository, be sure to add `-v "$(pwd)/shared":"/shared":rw` to your docker run command.
