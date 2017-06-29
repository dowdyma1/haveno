/*
 * This file is part of bisq.
 *
 * bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package io.bisq.common.locale;

import com.google.protobuf.Message;
import io.bisq.common.GlobalSettings;
import io.bisq.generated.protobuffer.PB;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Currency;
import java.util.Locale;

@EqualsAndHashCode(callSuper = true)
@ToString
@Getter
public final class FiatCurrency extends TradeCurrency {
    // http://boschista.deviantart.com/journal/Cool-ASCII-Symbols-214218618
    private final static String PREFIX = "★ ";

    private final Currency currency;

    public FiatCurrency(String currencyCode) {
        this(Currency.getInstance(currencyCode), getLocale());
    }

    @SuppressWarnings("WeakerAccess")
    public FiatCurrency(Currency currency) {
        this(currency, getLocale());
    }

    @SuppressWarnings("WeakerAccess")
    public FiatCurrency(Currency currency, Locale locale) {
        super(currency.getCurrencyCode(), currency.getDisplayName(locale));
        this.currency = currency;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // PROTO BUFFER
    ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Message toProtoMessage() {
        PB.Currency.Builder currencyBuilder = PB.Currency.newBuilder().setCurrencyCode(currency.getCurrencyCode());
        PB.FiatCurrency.Builder fiatCurrencyBuilder = PB.FiatCurrency.newBuilder().setCurrency(currencyBuilder);
        return getTradeCurrencyBuilder()
                .setFiatCurrency(fiatCurrencyBuilder)
                .build();
    }

    public static FiatCurrency fromProto(PB.TradeCurrency proto) {
        return new FiatCurrency(proto.getCode());
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////////////////////

    private static Locale getLocale() {
        return GlobalSettings.getLocale();
    }

    @Override
    public String getDisplayPrefix() {
        return PREFIX;
    }

}
