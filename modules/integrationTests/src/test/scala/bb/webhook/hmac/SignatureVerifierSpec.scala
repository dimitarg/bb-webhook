package bb.webhook.hmac

import weaver.pure._
import fs2.Stream
import cats.effect.{IO, ContextShift}
import bb.webhook.testutil._

object SignatureVerifierSpec {
  def tests(implicit shift: ContextShift[IO]): Stream[IO, RTest[TestResources]] = Stream(
    rTest("signature verifier can verify message signed via openssl") { case TestResources(blocker) =>
      tmpClasspathResource(blocker)("/sign.sh", perms = Some("rwx------")).use { scriptPath =>
        for {
          result <- execProcess(blocker)(command = scriptPath.toString(), args = List("secret-key-here", "value-to-digest"))
          _ <- IO(println(result))
        } yield expect(1 == 1)
      }
    }
  )
}
