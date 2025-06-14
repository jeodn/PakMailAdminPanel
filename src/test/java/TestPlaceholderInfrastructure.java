import com.entities.MailItem;
import com.infrastructure.*;
import com.usecases.NotifyCustomerOfMailInteractor;
import com.usecases.NotifyCustomerOfMailUseCase;
import jakarta.mail.MessagingException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class TestPlaceholderInfrastructure {
    @Test
    public void testNotifyCustomerStoresMailAndSendsEmail() throws MessagingException, IOException {
        MailRepository mailRepo = new InMemoryMailRepository();
        CustomerRepository customerRepo = new InMemoryCustomerRepository();
        FakeEmailService emailService = new FakeEmailService();

        NotifyCustomerOfMailUseCase useCase =
                new NotifyCustomerOfMailInteractor(mailRepo, customerRepo, emailService);

        File imageFile = new File("src/main/resources/repository-open-graph-template.png");

        MailItem mail = new MailItem(
                UUID.randomUUID(),
                34,
                "Amazon",
                LocalDate.now(),
                "Small package",
                imageFile,
                "package"
        );

        useCase.notifyCustomer(mail);

        System.out.println(mailRepo.findByCustomerId(34).getFirst().getDescription());
        System.out.println(emailService.getSentEmails().getFirst().getMailItem().getDescription());

        assertEquals(1, mailRepo.findByCustomerId(34).size());
        assertEquals(1, emailService.getSentEmails().size());
    }

}


