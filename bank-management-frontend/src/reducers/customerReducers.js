import {
  CUSTOMER_REGISTER_REQUEST,
  CUSTOMER_REGISTER_SUCCESS,
  CUSTOMER_REGISTER_FAIL,
  CUSTOMER_REGISTER_RESET,
  CUSTOMER_SEARCH_BY_IDENTITY_REQUEST,
  CUSTOMER_SEARCH_BY_IDENTITY_SUCCESS,
  CUSTOMER_SEARCH_BY_IDENTITY_FAIL,
  CUSTOMER_SEARCH_BY_ID_FAIL,
  CUSTOMER_SEARCH_BY_ID_SUCCESS,
  CUSTOMER_SEARCH_BY_ID_REQUEST,
  CUSTOMER_SEARCH_BY_IDENTITY_RESET,
  CUSTOMER_SEARCH_BY_ID_RESET,
  CUSTOMER_UPDATE_SALARY_REQUEST,
  CUSTOMER_UPDATE_SALARY_SUCCESS,
  CUSTOMER_UPDATE_SALARY_FAIL,
  CUSTOMER_UPDATE_SALARY_RESET,
  CUSTOMER_UPDATE_STATUS_REQUEST,
  CUSTOMER_UPDATE_STATUS_SUCCESS,
  CUSTOMER_UPDATE_STATUS_FAIL,
  CUSTOMER_UPDATE_STATUS_RESET,
} from "../constants/customerConstants";

export const customerRegisterReducer = (state = {}, action) => {
  switch (action.type) {
    case CUSTOMER_REGISTER_REQUEST:
      return { loading: true };
    case CUSTOMER_REGISTER_SUCCESS:
      return { loading: false, customerInfo: action.payload };
    case CUSTOMER_REGISTER_FAIL:
      return { loading: false, error: action.payload };
    case CUSTOMER_REGISTER_RESET:
      return {};
    default:
      return state;
  }
};

export const customerSearchByIdentityReducer = (
  state = { customerInfoByIdentity: {} },
  action
) => {
  switch (action.type) {
    case CUSTOMER_SEARCH_BY_IDENTITY_REQUEST:
      return { loading: true };
    case CUSTOMER_SEARCH_BY_IDENTITY_SUCCESS:
      return { loading: false, customerInfoByIdentity: action.payload };
    case CUSTOMER_SEARCH_BY_IDENTITY_FAIL:
      return { loading: false, error: action.payload };
    case CUSTOMER_SEARCH_BY_IDENTITY_RESET:
      return {
        customerInfo: {},
      };
    default:
      return state;
  }
};

export const customerSearchByIdReducer = (
  state = { customerInfoById: {} },
  action
) => {
  switch (action.type) {
    case CUSTOMER_SEARCH_BY_ID_REQUEST:
      return { loading: true };
    case CUSTOMER_SEARCH_BY_ID_SUCCESS:
      return { loading: false, customerInfoById: action.payload };
    case CUSTOMER_SEARCH_BY_ID_FAIL:
      return { loading: false, error: action.payload };
    case CUSTOMER_SEARCH_BY_ID_RESET:
      return {
        customerInfo: {},
      };
    default:
      return state;
  }
};

export const customerUpdateSalaryReducer = (
  state = { updatedSalaryCustomer: {} },
  action
) => {
  switch (action.type) {
    case CUSTOMER_UPDATE_SALARY_REQUEST:
      return { loading: true };
    case CUSTOMER_UPDATE_SALARY_SUCCESS:
      return { loading: false, updatedSalaryCustomer: action.payload };
    case CUSTOMER_UPDATE_SALARY_FAIL:
      return { loading: false, error: action.payload };
    case CUSTOMER_UPDATE_SALARY_RESET:
      return {
        customerInfo: {},
      };
    default:
      return state;
  }
};

export const customerUpdateStatusReducer = (
  state = { updatedStatusCustomer: {} },
  action
) => {
  switch (action.type) {
    case CUSTOMER_UPDATE_STATUS_REQUEST:
      return { loading: true };
    case CUSTOMER_UPDATE_STATUS_SUCCESS:
      return { loading: false, updatedStatusCustomer: action.payload };
    case CUSTOMER_UPDATE_STATUS_FAIL:
      return { loading: false, error: action.payload };
    case CUSTOMER_UPDATE_STATUS_RESET:
      return {
        customerInfo: {},
      };
    default:
      return state;
  }
};
