 [ ![Download](https://api.bintray.com/packages/dimitarg/maven/bb-webhook/images/download.svg) ](https://bintray.com/dimitarg/maven/bb-webhook/_latestVersion)
[![codecov](https://codecov.io/gh/dimitarg/bb-webhook/branch/master/graph/badge.svg?token=YV7y6sXisz)](https://codecov.io/gh/dimitarg/bb-webhook)
 ![Build status](https://github.com/dimitarg/bb-webhook/workflows/Continuous%20Integration/badge.svg?branch=master)


# bb-webhook

Under construction.

A minimal http server which listens to bitbucket webhook events, and executes a command, passing the
sanitised event payload as input.

# Installation

## Manual

1. Determine the latest version of the service by using the version badge at the top of this readme

2. Dowload the service using the following command, replacing `<ver>` with the latest version:

```
curl --location https://dl.bintray.com/dimitarg/maven/io/github/dimitarg/bb-webhook_2.13/<ver>/bb-webhook_2.13-<ver>-assembly.jar --output bb-webhook.jar
```

3. Run the server

```
java -jar bb-webhook.jar
```

# Design

## Goals

- Minimal: The code only spins up a http port to listen to + parse bitbucket webhook events, and pipe them
to an os command.
- Auditable: The non-test code in this repo is small enought that it is feasible to audit it, if you can read
functional Scala

## Non-goals

- Supporting other types of webhooks, such as Github, Gitlab etc, as that will conflict with the goals of being
minimal and auditable.

- SSL termination. You should do that externally to this service.

