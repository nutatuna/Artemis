from testUtils.AbstractProgramTest import AbstractProgramTest

from typing import List


class TestUBSan(AbstractProgramTest):
    """
    Test case that tries to compile the given program with undefined behavior sanitizer enabled.
    All warnings will be treated as errors and compilation will fail.
    Requires "libubsan" to be installed on your system.
    """

    makeTarget: str

    def __init__(self, executionDirectory: str, makeTarget: str = "ubsan", requirements: List[str] = None, name: str = "TestCompileUBSan"):
        super(TestUBSan, self).__init__(
            name, executionDirectory, "make", requirements, timeoutSec=5)
        self.makeTarget = makeTarget

    def _run(self):
        # Start the program:
        self.pWrap = self._createPWrap(
            [self.executable, "-C", self.executionDirectory, self.makeTarget])
        self._startPWrap(self.pWrap)

        self.pWrap.waitUntilTerminationReading()

        retCode: int = self.pWrap.getReturnCode()
        if retCode != 0:
            self._failWith("Make for directory {} failed. Returncode is {}.".format(
                str(self.executionDirectory), retCode))

        # Allways cleanup to make sure all threads get joined:
        self.pWrap.cleanup()