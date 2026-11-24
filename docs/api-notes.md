# FieldForce API Testing Notes

## 1. Environment and collection setup

- **Postman collection:** `postman/NiMap.postman_collection.json`
- **Postman environment:** `postman/FieldForce_Env.postman_environment.json`
- **Base URL variable:** `{{base_url}}`
- **Login variables:** `{{login_username}}` and `{{login_password}}`
- **Protected API header variable:** `{{basic_auth_1dau}}`

Set credentials locally in Postman. Do not commit real values to the repository.

## 2. Authentication behavior observed

The exported collection uses `POST {{base_url}}/api/account/authenticate` for valid and invalid login checks.

The valid-login response recorded in the assignment notes contained an empty `token` value. The exported Get Leads request uses `{{basic_auth_1dau}}` in its Authorization header. This is the observed setup in the supplied artifacts; do not assume the login token is reusable unless the API behavior is confirmed.

## 3. Requests present in the exported collection

| Request | Method | Endpoint | Purpose |
|---|---|---|---|
| valid login | POST | `{{base_url}}/api/account/authenticate` | Submit valid login credentials |
| invalid login | POST | `{{base_url}}/api/account/authenticate` | Verify invalid-credential response |
| Get Leads | POST | `{{base_url}}/api/CRM/Leads` | Retrieve/filter leads and validate response data |

### Get Leads assertions

The exported request includes assertions for:

1. HTTP status code is 200.
2. Response payload contains `Status == 200` and `Message == "Success"`.
3. The returned `data.Leads` array includes one of the named test records used in the collection assertion.

## 4. Add Customer API coverage — current limitation

The supplied Postman collection does **not** contain a dedicated Add Customer API request. The supplied project source shows an **Add Customer UI automation workflow** using Selenium/Cucumber, which is different from calling an API directly.

The manual-testing workbook currently lists TC-70 to TC-73 as Add Customer/API-related cases and marks them PASS. However, the exported collection does not provide the endpoint, request payload, response assertions, or execution evidence for those cases.

Therefore:

- Do not claim that the current Postman collection demonstrates Add Customer API testing.
- Keep the existing workbook results only if you have separate, genuine execution evidence for those cases.
- If the endpoint and payload are available from the application/API documentation or a captured successful request, add the real request and positive/negative assertions to Postman.
- Do not guess an endpoint, payload schema, status code, or response structure.

## 5. Import and run

1. Open Postman and choose **Import**.
2. Import the collection and environment JSON files.
3. Select the `FieldForce_Env` environment.
4. Set `base_url`, `login_username`, `login_password`, and the protected API credential variable locally.
5. Run the requests individually and inspect both the Postman test results and response body.
