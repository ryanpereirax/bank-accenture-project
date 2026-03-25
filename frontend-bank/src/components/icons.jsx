import React from "react";

const Base = ({ className, children }) => (
  <svg className={className} viewBox="0 0 24 24" fill="none">
    {children}
  </svg>
);

export const IGrid = ({ className }) => (
  <Base className={className}>
    <path d="M4 4h7v7H4V4Zm9 0h7v7h-7V4ZM4 13h7v7H4v-7Zm9 0h7v7h-7v-7Z" stroke="currentColor" strokeWidth="2"/>
  </Base>
);

export const IUsers = ({ className }) => (
  <Base className={className}>
    <path d="M9.5 11a3.5 3.5 0 1 0-3.5-3.5A3.5 3.5 0 0 0 9.5 11Z" stroke="currentColor" strokeWidth="2"/>
    <path d="M2.5 20c1.4-4 10.2-4 11.5 0" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
    <path d="M16 11a3 3 0 1 0-2.6-4.5" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
    <path d="M14 20c.6-1.8 2.2-2.8 4.5-3" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
  </Base>
);

export const IBank = ({ className }) => (
  <Base className={className}>
    <path d="M4 10h16" stroke="currentColor" strokeWidth="2"/>
    <path d="M6 10v10m4-10v10m4-10v10m4-10v10" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
    <path d="M4 10 12 4l8 6" stroke="currentColor" strokeWidth="2" strokeLinejoin="round"/>
    <path d="M4 20h16" stroke="currentColor" strokeWidth="2"/>
  </Base>
);

export const ICard = ({ className }) => (
  <Base className={className}>
    <path d="M4 7.5A2.5 2.5 0 0 1 6.5 5h11A2.5 2.5 0 0 1 20 7.5v9A2.5 2.5 0 0 1 17.5 19h-11A2.5 2.5 0 0 1 4 16.5v-9Z" stroke="currentColor" strokeWidth="2"/>
    <path d="M4 9h16" stroke="currentColor" strokeWidth="2"/>
    <path d="M7 15h4" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
  </Base>
);

export const ISwap = ({ className }) => (
  <Base className={className}>
    <path d="M7 7h12l-2-2" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
    <path d="M17 17H5l2 2" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
  </Base>
);

export const IReceipt = ({ className }) => (
  <Base className={className}>
    <path d="M7 3h10v18l-2-1-2 1-2-1-2 1-2-1-2 1V3Z" stroke="currentColor" strokeWidth="2" strokeLinejoin="round"/>
    <path d="M9 7h6M9 11h6M9 15h4" stroke="currentColor" strokeWidth="2" strokeLinecap="round"/>
  </Base>
);