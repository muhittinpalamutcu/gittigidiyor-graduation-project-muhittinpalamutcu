import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import Loader from "../components/Loader";
import Message from "../components/Message";
import {
  searchCustomerById,
  updateCustomerSalary,
  updateCustomerStatus,
} from "../actions/customerActions";
import {
  CUSTOMER_SEARCH_BY_ID_RESET,
  CUSTOMER_UPDATE_SALARY_RESET,
  CUSTOMER_UPDATE_STATUS_RESET,
} from "../constants/customerConstants";

const CustomerUpdateScreen = () => {
  const [id, setId] = useState("");
  const [customerUpdatable, setCustomerUpdatable] = useState(false);
  const [salary, setSalary] = useState("");
  const [activity, setActivity] = useState();

  const dispatch = useDispatch();

  const customerSearchById = useSelector((state) => state.customerSearchById);
  const {
    loading: loadingById,
    error: errorById,
    customerInfoById,
  } = customerSearchById;

  const customerUpdateSalary = useSelector(
    (state) => state.customerUpdateSalary
  );
  const { error: errorUpdateSalary, updatedSalaryCustomer } =
    customerUpdateSalary;

  const customerUpdateStatus = useSelector(
    (state) => state.customerUpdateStatus
  );
  const { updatedStatusCustomer } = customerUpdateStatus;

  useEffect(() => {
    if ((updatedSalaryCustomer || updatedStatusCustomer) && id !== "") {
      dispatch(searchCustomerById(id));
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [updatedSalaryCustomer, updatedStatusCustomer, dispatch]);

  useEffect(() => {
    dispatch({ type: CUSTOMER_SEARCH_BY_ID_RESET });
    dispatch({ type: CUSTOMER_UPDATE_SALARY_RESET });
    dispatch({ type: CUSTOMER_UPDATE_STATUS_RESET });
  }, [dispatch]);

  const submitHandler = (e) => {
    e.preventDefault();
    dispatch(searchCustomerById(id));
    dispatch({ type: CUSTOMER_UPDATE_SALARY_RESET });
    dispatch({ type: CUSTOMER_UPDATE_STATUS_RESET });
  };

  const updateHandler = () => {
    dispatch(
      updateCustomerSalary(id, salary === "" ? customerInfoById.salary : salary)
    );
    dispatch(
      updateCustomerStatus(
        id,
        activity === "" ? customerInfoById.active : activity
      )
    );
    setCustomerUpdatable(false);
    setSalary("");
    setActivity("");
  };

  return (
    <div>
      <h1 className="text-gray-900 font-bold text-xl">Update Customer By Id</h1>
      <hr className="border-primary border-b-2 mt-2" />
      <div className="grid grid-cols-2 gap-10">
        <form className="w-full mt-3">
          {loadingById && <Loader />}
          {errorById && (
            <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              {errorById}
            </Message>
          )}
          <div className="grid grid-cols-2 gap-4">
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700 uppercase">
                Please enter customer id to search
              </p>
              <input
                type="text"
                value={id}
                onChange={(e) => setId(e.target.value)}
                placeholder="id"
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
            {updatedSalaryCustomer ? (
              <Message messageStyle="bg-green-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
                Saved successfully
              </Message>
            ) : (
              updatedStatusCustomer && (
                <Message messageStyle="bg-green-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
                  Saved successfully
                </Message>
              )
            )}
            {errorUpdateSalary &&
              !updatedSalaryCustomer &&
              !updatedStatusCustomer && (
                <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
                  {errorUpdateSalary}
                </Message>
              )}
            <div className="shadow-md bg-gray-200 p-3">
              <div className="flex justify-between">
                <h3 className=" text-gray-900 font-bold text-base">
                  Customer Information
                </h3>
                <h4
                  className=" text-sm cursor-pointer text-red-500 hover:text-red-800 font-medium"
                  onClick={() =>
                    setCustomerUpdatable(customerUpdatable ? false : true)
                  }
                >
                  Update Customer
                </h4>
              </div>
              <p>Id : {customerInfoById.id}</p>
              <p>Full Name : {customerInfoById.fullName}</p>
              <p>Identity Number : {customerInfoById.identityNumber}</p>
              <p>Phone Number : {customerInfoById.phoneNumber}</p>
              {!customerUpdatable ? (
                <div>
                  <p>Salary : {customerInfoById.salary} TL</p>
                  <p className="text-blue-600 font-bold">
                    {customerInfoById.active
                      ? "Account is active"
                      : "Account is deactivate"}
                  </p>
                </div>
              ) : (
                <div>
                  <span>Salary</span>
                  <input
                    disabled={!customerInfoById.active}
                    type="text"
                    value={salary}
                    onChange={(e) => setSalary(e.target.value)}
                    placeholder={customerInfoById.salary}
                    className="w-4/12 pl-2 h-5 m-1 rounded-md border-2 bg-white hover:border-gray-700 border-transparent focus:bg-white focus:outline-none focus:ring-2 focus:ring-blue-900 focus:border-transparent"
                  />
                  <div onChange={(e) => setActivity(e.target.value)}>
                    <input
                      type="radio"
                      value={true}
                      name="activity"
                      defaultChecked={customerInfoById.active}
                    />{" "}
                    Active{" "}
                    <input
                      type="radio"
                      value={false}
                      name="activity"
                      defaultChecked={!customerInfoById.active}
                    />{" "}
                    Deactive
                  </div>
                  <div
                    onClick={updateHandler}
                    className="flex justify-end cursor-pointer text-blue-600 hover:text-blue-900"
                  >
                    Save Changes
                  </div>
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CustomerUpdateScreen;
