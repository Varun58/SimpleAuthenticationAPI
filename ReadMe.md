# Simple API with token authentication

Implement an API with a simple token-based authentication. The API should have the following endpoints.

## POST /users

Creates a user. Required parameters:

- username: string
- password: string

The user ID should be generated automatically and returned in the response.

### Responses

- 400 if the required fields are missing or invalid
- 201 if the user is created successfully

Successful response body:

```json
  {
    "id": "<new uuid>"
  }
```

## POST /auth/login

Authenticates a user by generating a token and linking it to the user account. Required parameters:

- username: string
- password: string

### Responses

- 401 if the username/password do not match
- 400 if the required fields are missing or invalid
- 200 if the user is authenticated successfully

The response body should return the auth token:

```json
  {
    "token": "<new token>"
  }
```

## POST /auth/logout

Requires the token to be passed in the request headers.

Invalidates that token.

### Responses

- 401 if the token is empty or invalid
- 200 if request is successful

The response body may be empty.

## POST /collections

Creates a new collection with autogenerated ID.

Requires the token to be passed in the request headers.

Required request body fields:

- title: string
- visibility: 'public' | 'private'

### Responses

- 400 if the body is empty or invalid
- 401 if the auth token is empty or invalid
- 201 if the collection was created successfully

The response body may contain the uuid of the new collection.

## GET /collections

### Responses

Response varies depending on whether the token is present and valid: if it is, the response will contain all public collections, and the private collections of the authenticated user. If not, it will only contain the public collections.

```json
[
  {
    "id": "id1",
    "author": "userid1",
    "visibility": "public"
  },
  {
    "id": "id2",
    "author": "currentuserid",
    "visibility": "private"
  }
]
```

# Assumptions

- There is no DB connection required, everything can be kept in memory
- The auth token can be sent as `x-api-token` header
- The token should be associated with a specific user
- The usernames are unique
- If a field is passed in a request body, we can assume it is a non-empty string
- (For Nodejs) You may use `uuid` and `lodash` libraries, among others, to simplify your solution.
