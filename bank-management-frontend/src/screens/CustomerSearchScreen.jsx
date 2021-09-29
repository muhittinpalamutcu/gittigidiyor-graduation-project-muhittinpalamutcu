import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Loader from "../components/Loader";
import Message from "../components/Message";
import {
  searchCustomerById,
  searchCustomerByIdentity,
} from "../actions/customerActions";
import {
  CUSTOMER_SEARCH_BY_IDENTITY_RESET,
  CUSTOMER_SEARCH_BY_ID_RESET,
} from "../constants/customerConstants";

const CustomerSearchScreen = () => {
  const [IdOridentityNumber, setIdOrIdentityNumber] = useState("");
  const [currentOption, setCurrentOption] = useState("id");

  const dispatch = useDispatch();

  const customerSearchById = useSelector((state) => state.customerSearchById);
  const {
    loading: loadingById,
    error: errorById,
    customerInfoById,
  } = customerSearchById;

  const customerSearchByIdentity = useSelector(
    (state) => state.customerSearchByIdentity
  );
  const {
    loading: loadingByIdentity,
    error: errorByIdentity,
    customerInfoByIdentity,
  } = customerSearchByIdentity;

  useEffect(() => {
    dispatch({ type: CUSTOMER_SEARCH_BY_ID_RESET });
    dispatch({ type: CUSTOMER_SEARCH_BY_IDENTITY_RESET });
  }, [dispatch]);

  const submitHandler = (e) => {
    e.preventDefault();
    if (currentOption === "id") {
      dispatch(searchCustomerById(IdOridentityNumber));
      dispatch({ type: CUSTOMER_SEARCH_BY_IDENTITY_RESET });
    } else {
      dispatch(searchCustomerByIdentity(IdOridentityNumber));
      dispatch({ type: CUSTOMER_SEARCH_BY_ID_RESET });
    }
  };

  const changeOption = (e) => {
    setCurrentOption(e);
  };

  return (
    <div>
      <div className="flex justify-start items-center gap-3">
        <h1 className="text-gray-900 font-bold text-xl">Search Customer By </h1>
        <div class="relative">
          <select
            onChange={(event) => changeOption(event.target.value)}
            value={currentOption}
            className="w-30 h-6 shadow-lg rounded-md border-2 bg-primary text-white hover:border-gray-700 border-transparent focus:bg-primary focus:outline-none focus:ring-2 focus:ring-blue-900 focus:border-transparent"
          >
            <option value="id">Id</option>
            <option value="identityNumber">Identity Number</option>
          </select>
        </div>
      </div>

      <hr className="border-primary border-b-2 mt-2" />
      <div className="grid grid-cols-2 gap-10">
        <form className="w-full mt-3">
          {loadingById && <Loader />}
          {loadingByIdentity && <Loader />}
          {errorById && !customerInfoById && (
            <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              {errorById}
            </Message>
          )}
          {errorByIdentity && !customerInfoByIdentity && (
            <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              {errorByIdentity}
            </Message>
          )}
          <div className="grid grid-cols-2 gap-4">
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700 uppercase">
                {currentOption === "id"
                  ? "Please enter customer id to search"
                  : "Please enter customer identity number to search"}
              </p>
              <input
                type="text"
                value={IdOridentityNumber}
                onChange={(e) => setIdOrIdentityNumber(e.target.value)}
                placeholder={currentOption === "id" ? "120" : "29904356366"}
                className="input"
              />
            </div>
          </div>
          <div className="flex justify-end mt-3">
            <button
              type="submit"
              onClick={submitHandler}
              className="btn text-white bg-primary ml-2 border-primary md:border-2 hover:bg-white hover:text-primary transition ease-out duration-500"
            >
              Find Customer
            </button>
          </div>
        </form>
        {customerInfoById && (
          <div className="p-5">
            <div className="shadow-md bg-gray-200 p-3">
              <h3 className=" text-gray-900 font-bold text-base">
                Customer Information
              </h3>
              <p>Id : {customerInfoById.id}</p>
              <p>Full Name : {customerInfoById.fullName}</p>
              <p>Identity Number : {customerInfoById.identityNumber}</p>
              <p>Phone Number : {customerInfoById.phoneNumber}</p>
              <p>Salary : {customerInfoById.salary} TL</p>
              <p className="text-blue-600 font-bold">
                {customerInfoById.active
                  ? "Account active"
                  : "Account deactivated"}
              </p>
            </div>
          </div>
        )}
        {customerInfoByIdentity && (
          <div className="p-5">
            <div className="shadow-md bg-gray-200 p-3">
              <h3 className=" text-green-900 font-bold text-base">
                Customer Information
              </h3>
              <p>Id : {customerInfoByIdentity.id}</p>
              <p>Full Name : {customerInfoByIdentity.fullName}</p>
              <p>Identity Number : {customerInfoByIdentity.identityNumber}</p>
              <p>Phone Number : {customerInfoByIdentity.phoneNumber}</p>
              <p>Salary : {customerInfoByIdentity.salary} TL</p>
              <p className="text-blue-600 font-bold">
                {customerInfoByIdentity.active
                  ? "Account active"
                  : "Account deactivated"}
              </p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CustomerSearchScreen;
