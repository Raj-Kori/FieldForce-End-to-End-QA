# FieldForce API Testing Notes

## 1. Environment & Collection Setup
* **Postman Collection:** `postman/NiMap.postman_collection.json`
* **Postman Environment:** `postman/FieldForce_Env.postman_environment.json` (`FieldForce_Env`)
* **Environment & Collection Variables:**
  * `{{base_url}}`: Base API host URL for Test Field Force (`https://test.fieldforceconnect.com`)
  * `{{basic_auth_1dau}}`: Authorization header credential used for authenticated CRM endpoints (stored as a secret variable; replace placeholder before running)
  * `{{bearer_token_0ut6}}`: Reserved token variable (marked secret)

---

## 2. Authentication Behavior Observation
* Calling `POST {{base_url}}/api/account/authenticate` with valid credentials returns `"token": ""` in the response payload.
* Because the login endpoint returns an empty token string, downstream protected requests (such as `POST {{base_url}}/api/CRM/Leads`) pass `{{basic_auth_1dau}}` in the `Authorization` request header.

---

## 3. Tested Endpoints Summary

| Request Name | Method | Endpoint | Auth / Headers | Validation & Expected Outcome |
| :--- | :--- | :--- | :--- | :--- |
| **valid login** | `POST` | `{{base_url}}/api/account/authenticate` | `No Auth` | HTTP `200 OK`; authenticates valid user credentials |
| **invalid login** | `POST` | `{{base_url}}/api/account/authenticate` | `No Auth` | HTTP `200 OK`; returns `"success": false` and `"errormessage": "Invalid Email Id / Mobile No or Password."` |
| **Get Leads** | `POST` | `{{base_url}}/api/CRM/Leads` | `Authorization: {{basic_auth_1dau}}` | HTTP `200 OK`; JSON body verifies `Status == 200`, `Message == "Success"`, and presence of created test customer records (`Pandu_2` / `pandu`) in `data.Leads` |

---

## 4. Automated Postman Assertions (`Get Leads`)
The `Get Leads` request includes three JavaScript test assertions in Postman:
1. **HTTP Status Check:** Verifies `pm.response.to.have.status(200)`.
2. **Payload Status & Message Check:** Asserts `jsonData.Status === 200` and `jsonData.Message === "Success"`.
3. **Customer/Lead Data Verification:** Searches `jsonData.data.Leads` to confirm test records (`Pandu_2` or `pandu`) are returned.

---

## 5. How to Import & Run in Postman
1. Open Postman and click **Import**.
2. Import both `postman/NiMap.postman_collection.json` and `postman/FieldForce_Env.postman_environment.json`.
3. Select **FieldForce_Env** from the top-right environment dropdown.
4. Set your `{{base_url}}` and `{{basic_auth_1dau}}` values in the environment/collection variables, then run the requests individually or via **Collection Runner**.