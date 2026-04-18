# Contributing Guide

## Branching standard

Use branch names with this format:

- feat/<short-description>
- fix/<short-description>
- chore/<short-description>
- docs/<short-description>
- refactor/<short-description>
- test/<short-description>

Examples:

- feat/add-request-validation
- fix/handle-missing-parameter

## Commit message standard (Conventional Commits)

Use this pattern:

<type>(optional-scope): <short summary>

Allowed types:

- feat
- fix
- docs
- style
- refactor
- perf
- test
- build
- ci
- chore
- revert

Examples:

- feat(api): add validation for productId and brandId
- fix(error-handler): normalize invalid request response
- ci(workflow): run gradle check on pull requests

## Pull request standard

Each pull request should include:

- Clear title following Conventional Commits format
- Description of what changed and why
- Validation evidence (for example: ./gradlew check)

## Quality gate before merge

Run locally before pushing:

- ./gradlew check

A pull request must pass all GitHub Actions checks before merge.
