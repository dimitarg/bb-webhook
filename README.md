# bb-webhook

Under construction.

A minimal http server which listens to bitbucket webhook events, and executes a command, passing the
sanitised event payload as input.

# Installation

TODO.


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

