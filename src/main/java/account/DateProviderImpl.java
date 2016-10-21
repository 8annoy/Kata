package account;

import java.time.LocalDate;

/**
 * Created by eitannoy on 10/22/16.
 */
public class DateProviderImpl implements DateProvider {
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
