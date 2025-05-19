package finalproject;





	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	@RestController
	@RequestMapping("/api/transactions")
	@CrossOrigin(origins = "*") // Allow frontend (different origin) to call this
	public class TransactionController {

	    @PostMapping
	    public ResponseEntity<String> addTransaction(@RequestBody Transaction tx) {
	        System.out.println("Received transaction: " + tx.getDescription() + ", ₹" + tx.getAmount());
	        // TODO: Save to DB if needed
	        return ResponseEntity.ok("Transaction received successfully");
	    }
	}


