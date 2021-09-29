import { createStore, combineReducers, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import { composeWithDevTools } from "redux-devtools-extension";
import {
  customerRegisterReducer,
  customerSearchByIdentityReducer,
  customerSearchByIdReducer,
  customerUpdateSalaryReducer,
  customerUpdateStatusReducer,
} from "./reducers/customerReducers";
import { creditInformationInquiryReducer } from "./reducers/creditReducers";

const reducer = combineReducers({
  customerRegister: customerRegisterReducer,
  customerSearchByIdentity: customerSearchByIdentityReducer,
  customerSearchById: customerSearchByIdReducer,
  customerUpdateSalary: customerUpdateSalaryReducer,
  customerUpdateStatus: customerUpdateStatusReducer,
  creditInformationInquiry: creditInformationInquiryReducer,
});

const customerInfoFromStorage = localStorage.getItem("customerInfo")
  ? JSON.parse(localStorage.getItem("customerInfo"))
  : null;

const initialState = {
  customerRegister: { customerInfo: customerInfoFromStorage },
};

const middleware = [thunk];

const store = createStore(
  reducer,
  initialState,
  composeWithDevTools(applyMiddleware(...middleware))
);

export default store;
