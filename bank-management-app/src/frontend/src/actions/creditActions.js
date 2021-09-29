import {
  CREDIT_INFORMATION_INQUIRY_FAIL,
  CREDIT_INFORMATION_INQUIRY_REQUEST,
  CREDIT_INFORMATION_INQUIRY_SUCCESS,
} from "../constants/creditConstants";
import axios from "axios";

export const inquiryCreditInformation =
  (identityNumber) => async (dispatch) => {
    try {
      dispatch({
        type: CREDIT_INFORMATION_INQUIRY_REQUEST,
      });

      const config = {
        headers: {
          "Content-Type": "application/json",
        },
      };

      const { data } = await axios.get(
        `/api/credit-applications?customerIdentityNumber=${identityNumber}`,
        config
      );

      dispatch({
        type: CREDIT_INFORMATION_INQUIRY_SUCCESS,
        payload: data,
      });
    } catch (error) {
      dispatch({
        type: CREDIT_INFORMATION_INQUIRY_FAIL,
        payload:
          error.response && error.response.data.message
            ? error.response.data.message
            : error.message,
      });
    }
  };
