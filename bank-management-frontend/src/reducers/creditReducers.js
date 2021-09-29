import {
  CREDIT_APPLICATION_FAIL,
  CREDIT_APPLICATION_REQUEST,
  CREDIT_APPLICATION_RESET,
  CREDIT_APPLICATION_SUCCESS,
  CREDIT_INFORMATION_INQUIRY_FAIL,
  CREDIT_INFORMATION_INQUIRY_REQUEST,
  CREDIT_INFORMATION_INQUIRY_RESET,
  CREDIT_INFORMATION_INQUIRY_SUCCESS,
} from "../constants/creditConstants";

export const creditInformationInquiryReducer = (state = {}, action) => {
  switch (action.type) {
    case CREDIT_INFORMATION_INQUIRY_REQUEST:
      return { loading: true };
    case CREDIT_INFORMATION_INQUIRY_SUCCESS:
      return { loading: false, creditInformation: action.payload };
    case CREDIT_INFORMATION_INQUIRY_FAIL:
      return { loading: false, error: action.payload };
    case CREDIT_INFORMATION_INQUIRY_RESET:
      return {
        creditInfo: [],
      };
    default:
      return state;
  }
};

export const creditApplicationReducer = (state = {}, action) => {
  switch (action.type) {
    case CREDIT_APPLICATION_REQUEST:
      return { loading: true };
    case CREDIT_APPLICATION_SUCCESS:
      return { loading: false, creditInfo: action.payload };
    case CREDIT_APPLICATION_FAIL:
      return { loading: false, error: action.payload };
    case CREDIT_APPLICATION_RESET:
      return {};
    default:
      return state;
  }
};
