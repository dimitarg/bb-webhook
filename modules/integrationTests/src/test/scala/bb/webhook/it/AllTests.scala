package bb.webhook.it

import weaver.pure._
import cats.effect.IO
import fs2.Stream
import bb.webhook.Main
import cats.effect.ExitCode

object AllTests extends Suite {

  override def suitesStream: fs2.Stream[IO,RTest[Unit]] = Stream(
    test("the application can start") {
      Main.run(List()).map { result =>
        expect(result == ExitCode.Success)
      }
    }
  )
}
