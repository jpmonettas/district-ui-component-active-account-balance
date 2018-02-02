(ns district.ui.component.active-account-balance
  (:require
    [district.format :as format]
    [district.ui.web3-account-balances.subs :as balances-subs]
    [re-frame.core :refer [subscribe]]))

(defn active-account-balance []
  (fn [{:keys [:contract :token-code :locale :max-fraction-digits :min-fraction-digits]
        :or {contract :ETH max-fraction-digits 2 min-fraction-digits 0}
        :as props}]
    (let [balance @(subscribe [::balances-subs/active-account-balance contract])]
      (when balance
        [:div.active-account-balance
         (dissoc props :contract :token-code :locale :max-fraction-digits :min-fraction-digits)
         [:span.balance
          (format/format-number balance (merge
                                          {:max-fraction-digits max-fraction-digits
                                           :min-fraction-digits min-fraction-digits}
                                          (when locale
                                            {:locale locale})))]
         " "
         [:span.token-code (or token-code (name contract))]]))))