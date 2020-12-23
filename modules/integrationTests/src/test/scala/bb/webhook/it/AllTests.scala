package bb.webhook.it

import weaver.pure._
import cats.effect.IO
import fs2.Stream
import bb.webhook.AppSpec
import bb.webhook.hmac._
import bb.webhook.testutil._

object AllTests extends Suite {

  override def suitesStream: Stream[IO, Test] = 
    Stream.resource(mkTestResources).flatMap { testResources =>
      AppSpec.tests ++
      SignatureVerifierSpec.tests.provide(testResources)
    }
}
