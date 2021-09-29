import React from "react";

const Loader = () => {
  return (
    <button
      type="button"
      className="bg-gray-200 flex justify-center w-6/12 text-black mt-2"
      disabled
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        className="animate-spin h-5 w-5 mr-3"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M20 7l-8-4-8 4m16 0l-8 4m8-4v10l-8 4m0-10L4 7m8 4v10M4 7v10l8 4"
        />
      </svg>
      Processing
    </button>
  );
};

export default Loader;
