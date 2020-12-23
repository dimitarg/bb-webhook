package bb.webhook.it

import weaver.pure._
import cats.effect.IO
import fs2.Stream
import bb.webhook.AppSpec
import bb.webhook.hmac._
import bb.webhook.testutil._
import cats.effect.Resource

object AllTests extends RSuite {

  override type R = TestResources

  override val sharedResource: Resource[IO, TestResources] = mkTestResources

  override def suitesStream: Stream[IO,RTest[TestResources]] = 
    AppSpec.tests.using[TestResources](_ => ()) ++
    SignatureVerifierSpec.tests
}
