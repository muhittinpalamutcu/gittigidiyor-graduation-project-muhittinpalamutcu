import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { applicationCredit } from "../actions/creditActions";
import Loader from "../components/Loader";
import Message from "../components/Message";
import { CREDIT_APPLICATION_RESET } from "../constants/creditConstants";

const CreditApplicationScreen = () => {
  const [identityNumber, setIdentityNumber] = useState("");
  const [identityNumberForTable, setIdentityNumberForTable] = useState("");

  const dispatch = useDispatch();

  const creditApplication = useSelector((state) => state.creditApplication);
  const { loading, error, creditInfo } = creditApplication;

  useEffect(() => {
    if (creditInfo) {
      setIdentityNumberForTable(identityNumber);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [creditInfo]);

  useEffect(() => {
    dispatch({ type: CREDIT_APPLICATION_RESET });
  }, [dispatch]);

  const submitHandler = (e) => {
    e.preventDefault();
    if (identityNumber !== "") {
      dispatch(applicationCredit(identityNumber));
    }
  };
  return (
    <div>
      <h1 className="text-gray-900 font-bold text-xl">Credit Application</h1>
      <hr className="border-primary border-b-2 mt-2" />
      <div className="grid grid-cols-7 gap-10">
        <form className="w-full mt-3 col-span-3">
          {loading && <Loader />}
          {error && (
            <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              {error}
            </Message>
          )}
          {creditInfo && (
            <>
              {creditInfo.id && (
                <Message messageStyle="bg-green-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1 mt-2">
                  New credit application received for customer
                </Message>
              )}
            </>
          )}
          <div className="grid grid-cols-2 gap-4">
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700 uppercase">
                Please enter customer identity to apply for credit
              </p>
              <input
                type="text"
                value={identityNumber}
                onChange={(e) => setIdentityNumber(e.target.value)}
                placeholder="Identity number"
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
              Apply for credit
            </button>
          </div>
        </form>
        {creditInfo && (
          <>
            <div className="p-5 col-span-4">
              <div className="shadow-md bg-gray-200 p-3">
                <h3 className=" text-gray-900 font-bold text-base">
                  Existed Credit Applications
                </h3>
                <table className="w-full table-auto border mt-3">
                  <thead>
                    <tr className="text-left">
                      <th>Identity Number</th>
                      <th>Status</th>
                      <th>Credit Limit</th>
                      <th>Application Date</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td>{identityNumberForTable}</td>
                      <td>{creditInfo.status}</td>
                      <td>{creditInfo.creditLimit} TL</td>
                      <td>{creditInfo.applicationDate.substring(0, 10)}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default CreditApplicationScreen;
