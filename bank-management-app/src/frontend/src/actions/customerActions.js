import {
  CUSTOMER_REGISTER_FAIL,
  CUSTOMER_REGISTER_REQUEST,
  CUSTOMER_REGISTER_SUCCESS,
  CUSTOMER_REMOVE_FROM_STORAGE,
  CUSTOMER_SEARCH_BY_IDENTITY_FAIL,
  CUSTOMER_SEARCH_BY_IDENTITY_REQUEST,
  CUSTOMER_SEARCH_BY_IDENTITY_SUCCESS,
  CUSTOMER_SEARCH_BY_ID_FAIL,
  CUSTOMER_SEARCH_BY_ID_REQUEST,
  CUSTOMER_SEARCH_BY_ID_SUCCESS,
  CUSTOMER_UPDATE_SALARY_FAIL,
  CUSTOMER_UPDATE_SALARY_REQUEST,
  CUSTOMER_UPDATE_SALARY_SUCCESS,
  CUSTOMER_UPDATE_STATUS_FAIL,
  CUSTOMER_UPDATE_STATUS_REQUEST,
  CUSTOMER_UPDATE_STATUS_SUCCESS,
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

export const searchCustomerByIdentity =
  (identityNumber) => async (dispatch) => {
    try {
      dispatch({
        type: CUSTOMER_SEARCH_BY_IDENTITY_REQUEST,
      });

      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };

      const { data } = await axios.get(
        `/api/customers?identity=${identityNumber}`,
        config
      );

      dispatch({
        type: CUSTOMER_SEARCH_BY_IDENTITY_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: CUSTOMER_SEARCH_BY_IDENTITY_FAIL,
        payload:
          error.response && error.response.data.message
            ? error.response.data.message
            : error.message,
      });
    }
  };

export const searchCustomerById = (id) => async (dispatch) => {
  try {
    dispatch({
      type: CUSTOMER_SEARCH_BY_ID_REQUEST,
    });

    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    const { data } = await axios.get(`/api/customers/${id}`, config);

    dispatch({
      type: CUSTOMER_SEARCH_BY_ID_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: CUSTOMER_SEARCH_BY_ID_FAIL,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};

export const updateCustomerSalary = (id, salary) => async (dispatch) => {
  try {
    dispatch({
      type: CUSTOMER_UPDATE_SALARY_REQUEST,
    });

    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    const { data } = await axios.patch(
      `/api/customers/${id}/salary`,
      salary,
      config
    );

    dispatch({
      type: CUSTOMER_UPDATE_SALARY_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: CUSTOMER_UPDATE_SALARY_FAIL,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};

export const updateCustomerStatus = (id, status) => async (dispatch) => {
  try {
    dispatch({
      type: CUSTOMER_UPDATE_STATUS_REQUEST,
    });

    const config = {
      headers: {
        "Content-Type": "application/json",
      },
    };

    const { data } = await axios.patch(
      `/api/customers/${id}/status`,
      status,
      config
    );

    dispatch({
      type: CUSTOMER_UPDATE_STATUS_SUCCESS,
      payload: data,
    });
  } catch (error) {
    dispatch({
      type: CUSTOMER_UPDATE_STATUS_FAIL,
      payload:
        error.response && error.response.data.message
          ? error.response.data.message
          : error.message,
    });
  }
};
