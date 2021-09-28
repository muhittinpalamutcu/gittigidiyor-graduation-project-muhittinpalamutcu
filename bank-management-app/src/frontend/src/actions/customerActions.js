import {
  CUSTOMER_REGISTER_FAIL,
  CUSTOMER_REGISTER_REQUEST,
  CUSTOMER_REGISTER_SUCCESS,
  CUSTOMER_REMOVE_FROM_STORAGE,
} from "../constants/customerConstants";
import axios from "axios";

export const register =
  (identityNumber, firstName, lastName, phoneNumber, salary) =>
  async (dispatch) => {
    try {
      dispatch({
        type: CUSTOMER_REGISTER_REQUEST,
      });

      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };

      const { data } = await axios.post(
        "/api/customers",
        { identityNumber, firstName, lastName, phoneNumber, salary },
        config
      );

      dispatch({
        type: CUSTOMER_REGISTER_SUCCESS,
        payload: data,
      });

      localStorage.setItem("customerInfo", JSON.stringify(data));
    } catch (error) {
      dispatch({
        type: CUSTOMER_REGISTER_FAIL,
        payload:
          error.response && error.response.data.message
            ? error.response.data.message
            : error.message,
      });
    }
  };

export const removeExistedCustomerFromStorage = () => (dispatch) => {
  localStorage.removeItem("customerInfo");
  dispatch({
    type: CUSTOMER_REMOVE_FROM_STORAGE,
  });
};
