inherit update-rc.d module

INITSCRIPT_NAME = "lddmodules"
INITSCRIPT_PARAMS = "defaults 99"

LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/KrishnenduMarathe/assignment7-aesd-krishnendumarathe;protocol=ssh;branch=main file://S98lddmodules"

PV = "1.0+git${SRCPV}"

SRCREV = "197c04fcacbccd42077165fef0e27d5dc81bde70"

S = "${WORKDIR}/git"

FILES:${PN} += "${sysconfdir}/init.d/lddmodules"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_BUILDDIR} ccflags-y='-I${S}/include' INSTALL_MOD_DIR=extra"

MODULE_SUBDIRS ?= "scull misc-modules"

do_configure () {
	:
}

do_compile () {
    for modloc in ${MODULE_SUBDIRS}; do
        oe_runmake -C ${STAGING_KERNEL_BUILDDIR} M=${S}/$modloc modules
    done
}

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S98lddmodules ${D}${sysconfdir}/init.d/lddmodules

        # install modules to rootfs
        for modloc in ${MODULE_SUBDIRS}; do
            oe_runmake -C ${STAGING_KERNEL_BUILDDIR} M=${S}/$modloc INSTALL_MOD_PATH=${D} modules_install
        done
}
