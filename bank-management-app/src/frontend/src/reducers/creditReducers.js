import {
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
