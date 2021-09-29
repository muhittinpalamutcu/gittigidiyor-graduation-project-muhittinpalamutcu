import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { inquiryCreditInformation } from "../actions/creditActions";
import { CREDIT_INFORMATION_INQUIRY_RESET } from "../constants/creditConstants";
import Loader from "../components/Loader";
import Message from "../components/Message";

const CreditInformationInquiryScreen = () => {
  const [identityNumber, setIdentityNumber] = useState("");
  const [identityNumberForTable, setIdentityNumberForTable] = useState("");

  const dispatch = useDispatch();

  const creditInformationInquiry = useSelector(
    (state) => state.creditInformationInquiry
  );
  const { loading, error, creditInformation } = creditInformationInquiry;

  useEffect(() => {
    if (creditInformation) {
      setIdentityNumberForTable(identityNumber);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [creditInformation]);

  useEffect(() => {
    dispatch({ type: CREDIT_INFORMATION_INQUIRY_RESET });
  }, [dispatch]);

  const submitHandler = (e) => {
    e.preventDefault();
    if (identityNumber !== "") {
      dispatch(inquiryCreditInformation(identityNumber));
    }
  };
  return (
    <div>
      <h1 className="text-gray-900 font-bold text-xl">
        Credit Information Inquiry
      </h1>
      <hr className="border-primary border-b-2 mt-2" />
      <div className="grid grid-cols-7 gap-10">
        <form className="w-full mt-3 col-span-3">
          {loading && <Loader />}
          {error && (
            <Message messageStyle="bg-red-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1">
              {error}
            </Message>
          )}
          <div className="grid grid-cols-2 gap-4">
            <div className="col-span-2">
              <p className="text-sm font-bold mb-1 text-gray-700 uppercase">
                Please enter customer identity number to inquiry
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
              Find applications
            </button>
          </div>
        </form>
        {creditInformation && (
          <>
            <div className="p-5 col-span-4">
              <div className="shadow-md bg-gray-200 p-3">
                <h3 className=" text-gray-900 font-bold text-base">
                  Existed Credit Applications
                </h3>
                {creditInformation.length === 0 ? (
                  <Message messageStyle="bg-green-500 text-white w-full h-8 rounded-sm mb-2 pl-2 pt-1 mt-2">
                    You don't have any existed credit applications
                  </Message>
                ) : (
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
                      {creditInformation.map((application) => {
                        return (
                          <tr>
                            <td>{identityNumberForTable}</td>
                            <td>{application.status}</td>
                            <td>{application.creditLimit} TL</td>
                            <td>
                              {application.applicationDate.substring(0, 10)}
                            </td>
                          </tr>
                        );
                      })}
                    </tbody>
                  </table>
                )}
              </div>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default CreditInformationInquiryScreen;
