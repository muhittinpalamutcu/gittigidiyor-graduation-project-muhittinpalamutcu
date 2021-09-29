import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import {
  register,
  removeExistedCustomerFromStorage,
} from "../actions/customerActions";
import Loader from "../components/Loader";
import Message from "../components/Message";
import { CUSTOMER_REGISTERY_RESET } from "../constants/customerConstants";

const CustomerRegisteryScreen = ({ history }) => {
  const [identityNumber, setIdentityNumber] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [salary, setSalary] = useState("");
  const [newRegistery, setNewRegistery] = useState(false);

  const dispatch = useDispatch();

  const customerRegister = useSelector((state) => state.customerRegister);
  const { loading, error, customerInfo } = customerRegister;

  useEffect(() => {
    if (customerInfo) {
      if (customerInfo.identityNumber) {
        setNewRegistery(false);
      }
    }
  }, [history, customerInfo]);

  const submitHandler = (e) => {
    e.preventDefault();
    dispatch(
      register(identityNumber, firstName, lastName, phoneNumber, salary)
    );
  };

  const newRegisterHandler = () => {
    setIdentityNumber("");
    setFirstName("");
    setLastName("");
    setPhoneNumber("");
    setSalary("");
    dispatch(removeExistedCustomerFromStorage());
    dispatch({ type: CUSTOMER_REGISTERY_RESET });
    setNewRegistery(true);
  };

  return (
    <div>
      <h1 className="text-gray-900 font-bold text-xl">Customer Registery</h1>
      <hr className="border-primary border-b-2 mt-2" />
      <div className="grid grid-cols-2 gap-10">
        <form className="w-full mt-3">
          {loading && <Loader />}
          {customerInfo && !newRegistery && (
            <Message messageStyle="bg-green-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              Registered successfully
            </Message>
          )}
          {error && (
            <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              {error}
            </Message>
          )}
          <div className="grid grid-cols-2 gap-4">
            <div>
              <p className="text-sm font-bold mb-1 text-gray-700">FIRST NAME</p>
              <input
                type="text"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                placeholder="John"
                className="input"
              />
            </div>
            <div>
              <p className="text-sm font-bold mb-1 text-gray-700">LAST NAME</p>
              <input
                type="text"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                placeholder="Doe"
                className="input"
              />
            </div>
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700">
                IDENTITY NUMBER
              </p>
              <input
                type="text"
                value={identityNumber}
                onChange={(e) => setIdentityNumber(e.target.value)}
                placeholder="29904356366"
                className="input"
              />
            </div>
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700">
                PHONE NUMBER
              </p>
              <input
                type="tel"
                value={phoneNumber}
                onChange={(e) => setPhoneNumber(e.target.value)}
                placeholder="05555252525"
                className="input"
              />
            </div>
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700">SALARY</p>
              <input
                type="text"
                value={salary}
                onChange={(e) => setSalary(e.target.value)}
                placeholder="10000"
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
              Save customer
            </button>
          </div>
        </form>
        {customerInfo && !newRegistery && (
          <div className="p-5">
            <div className="shadow-md bg-gray-200 p-3">
              <h3 className=" text-gray-900 font-bold text-base">
                Customer Information
              </h3>
              <p>Id : {customerInfo.id}</p>
              <p>Full Name : {customerInfo.fullName}</p>
              <p>Identity Number : {customerInfo.identityNumber}</p>
              <p>Phone Number : {customerInfo.phoneNumber}</p>
              <p>Salary : {customerInfo.salary} TL</p>
              <p className="text-blue-600 font-bold">
                {customerInfo.active ? "Account active" : "Account deactivated"}
              </p>
              <div className="flex justify-end w-full">
                <p
                  className="font-semibold text-gray-400 hover:text-gray-700 cursor-pointer"
                  onClick={newRegisterHandler}
                >
                  New registery?
                </p>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CustomerRegisteryScreen;
