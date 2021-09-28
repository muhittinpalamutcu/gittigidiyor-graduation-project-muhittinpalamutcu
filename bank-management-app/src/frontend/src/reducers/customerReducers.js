import {
  CUSTOMER_REGISTER_REQUEST,
  CUSTOMER_REGISTER_SUCCESS,
  CUSTOMER_REGISTER_FAIL,
  CUSTOMER_REGISTERY_RESET,
  CUSTOMER_SEARCH_BY_IDENTITY_REQUEST,
  CUSTOMER_SEARCH_BY_IDENTITY_SUCCESS,
  CUSTOMER_SEARCH_BY_IDENTITY_FAIL,
  CUSTOMER_SEARCH_BY_ID_FAIL,
  CUSTOMER_SEARCH_BY_ID_SUCCESS,
  CUSTOMER_SEARCH_BY_ID_REQUEST,
  CUSTOMER_SEARCH_BY_IDENTITY_RESET,
  CUSTOMER_SEARCH_BY_ID_RESET,
} from "../constants/customerConstants";

export const customerRegisterReducer = (state = {}, action) => {
  switch (action.type) {
    case CUSTOMER_REGISTER_REQUEST:
      return { loading: true };
    case CUSTOMER_REGISTER_SUCCESS:
      return { loading: false, customerInfo: action.payload };
    case CUSTOMER_REGISTER_FAIL:
      return { loading: false, error: action.payload };
    case CUSTOMER_REGISTERY_RESET:
      return {
        customerInfo: {},
      };
    default:
      return state;
  }
};

export const customerSearchByIdentityReducer = (state = {}, action) => {
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

export const customerSearchByIdReducer = (state = {}, action) => {
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
