# JGroups Coursework Template

[![Rolling Docker Release](https://github.com/scc311/jgroups-template/actions/workflows/rolling.yml/badge.svg?branch=main)](https://github.com/scc311/jgroups-template/actions/workflows/rolling.yml)

This is a **template** repository that you can use for the final stage of the SCC311 coursework with Docker. An example program that uses both Java RMI and JGroups has been provided here.

> ⚠️  **Warning**: Having coursework on a _public_ repository can have particularly serious consequences (even after you graduate)!

## Why?

This template makes it easy to start building your coursework with source control and docker, all you need is a GitHub account and Docker installed. Because this uses docker, you don't need any Java versions installed on you machine and you can easily build and run all components of the work (including the registry and the addition of `jgroups` to the Java classpath).

## Testing

Below is a table showing which platforms this has been tested on. If this works on a platform not yet verified as working (and you are comfortable using GitHub), please update this table in a Pull Request.

 | Docker for MacOS | Docker for Linux | Docker for Windows (WSL2 Backend) |
 | :--------------: | :--------------: | :-------------------------------: |
 |        ✅         |        ✅         |                 ❓                 |

---

## Using Template Repositories

 - Click the `Use this template` button on GitHib
  - When creating your version of the repository, **make sure it is a private repo**! 🔒
  - Clone your newly created repository onto you machine (or use codespaces)
  - 🎉 And its done

Optionally, you can also set up the github actions CI/CD pipeline to automatically build your docker container images and push them to the github container registry. You should probably also change what is in this `README` file to something more relevant.

---

## Getting Started

For the final coursework, you will need to create 3 separate entities that communicate via networks: a client (both a buyer and seller), a frontend server, and a replica server. For this, you may use the directory structure from this repository.

 - Your **client**: uses code from both the dir `client` and `api` to compile and should have access to `shared` at runtime.
 - Your **frontend**: uses code from `frontend`, `api`, and `utility` to compile and should have access to `shared` at runtime.
 - Your **backend** replicas: use code from `backend` and `utility` to compile and needs no directories at runtime.

Each directory that contains code is its own package, so compilation should happen from the root directory of this repository.

## Using Docker

If you are using docker, each directory that contains a `main` function has a `Dockerfile`. Each of these `Dockerfile`s use multi-stage building so that built docker images don't contain the full JDK, just the JRE (so a smaller image size). This means that after each code edit you will need to run the relevant `docker build` before re-running.

### Docker Compose

To make this process quicker, you can use `docker compose`! This will automatically build your containers and run them in one command. It can also make it easier to scale your backend replica servers.

If you are using `docker compose`, remember that each of the 3 services takes time to instantiate and launch _after_ the container has started. This means that you will likely encounter errors if you try and bring it all `up` at once. The commands that you may be best using are:

For compilation:
```bash
docker compose build # will build all 3 services
```

For the _backends_:
```bash
docker compose up --scale backend=5 backend # to start your backend replicas
docker compose down backend # to remove your replicas
```

For the _frontend_:
```bash
docker compose up frontend # to start your frontend
docker compose down frontend # to remove your frontend
```

For the _clients_:
```bash
docker compose up --scale client=7 client # to start your clients
docker compose down client # to remove your clients
```

> For systems that have the old (python-based) `docker-compose` tool installed, the above commands will need the `docker compose` changed to `docker-compose`

### Environment Variables

The utility package contains a class called `GroupUtils`. This provides the code to connect to a JChannel. Much like with RMI, the service is attached to a key, in this case a string. So that this is not baked in during the compilation (a bad practice), this can read the data from an **environment variable** called `GROUP`. The `docker-compose.yml` file shows how to set these when using `docker compose`, however, these can easily be set when running containers by the `docker run` command too: `docker run ... -e GROUP=EXAMPLE ...`. And you can set env vars when not using docker too like so: `export GROUP=EXAMPLE_NO_DOCKER`.
